package cz.richter.david.astroants.shortestpath

import es.usc.citius.hipster.graph.GraphEdge
import es.usc.citius.hipster.graph.HipsterMutableGraph
import es.usc.citius.hipster.graph.UndirectedEdge
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

open class ConcurrentHashBasedHipsterGraph<V, E> : HipsterMutableGraph<V, E> {
    private var connected: ConcurrentMap<V, MutableSet<GraphEdge<V, E>>> = ConcurrentHashMap(90000)

    override fun add(v: V): Boolean {
        //add a new entry to the hash map if it does not exist
        return connected.computeIfAbsent(v, {Collections.newSetFromMap(ConcurrentHashMap())}) == null
    }

    override fun add(vararg vertices: V): Set<V> {
        return vertices.filterTo(HashSet()) { add(it) }
    }

    override fun remove(v: V): Boolean {
        // Remove all edges related to v
        val edges = this.connected[v] ?: return false

        val it = edges.iterator()
        while (it.hasNext()) {
            // Remove the edge in the list of the selected vertex
            val edge = it.next()
            it.remove()

            val v2 = if (edge.vertex1 == v) edge.vertex2 else edge.vertex1
            val it2 = this.connected[v2]!!.iterator()
            while (it2.hasNext()) {
                val edge2 = it2.next()
                if (edge2.vertex1 == v || edge2.vertex2 == v) {
                    it2.remove()
                }
            }
        }
        this.connected.remove(v)
        return true
    }

    override fun remove(vararg vertices: V): Set<V> {
        return vertices.filterTo(HashSet()) { remove(it) }
    }

    override fun connect(v1: V?, v2: V?, value: E): GraphEdge<V, E> {
        // Check non-null arguments
        if (v1 == null || v2 == null) throw IllegalArgumentException("Invalid vertices. A vertex cannot be null")
        // Ensure that the vertices are in the graph
        if (!connected.containsKey(v1)) throw IllegalArgumentException(v1.toString() + " is not a vertex of the graph")
        if (!connected.containsKey(v2)) throw IllegalArgumentException(v2.toString() + " is not a vertex of the graph")
        val edge = buildEdge(v1, v2, value)
        // Associate the vertices with their edge
        connected[v1]!!.add(edge)
        connected[v2]!!.add(edge)
        return edge
    }

    open fun buildEdge(v1: V, v2: V, value: E): GraphEdge<V, E> {
        return UndirectedEdge(v1, v2, value)
    }

    private fun createEntry(vertex: V, edge: GraphEdge<V, E>): Map.Entry<V, GraphEdge<V, E>> {
        return object : Map.Entry<V, GraphEdge<V, E>> {
            override val key: V
                get() = vertex
            override val value: GraphEdge<V, E>
                get() = edge
        }
    }

    protected fun vedges(): Iterable<Map.Entry<V, GraphEdge<V, E>>> {
        return connected.entries.asSequence()
                .flatMap { entry ->
                    entry.value.asSequence().map {
                        createEntry(entry.key, it)
                    }
                }.asIterable()
    }

    /**
     * Returns a list of the edges in the graph.
     * @return edges of the graph.
     */
    override fun edges(): Iterable<GraphEdge<V, E>> {
        return vedges().asSequence().map { it.value }.asIterable()
    }

    /**
     * Returns the vertices of the graph. Any changes in the
     * returned iterator affect the underlying graph structure.
     * @return iterator with the vertices of the graph
     */
    override fun vertices(): Iterable<V> {
        return connected.keys
    }

    override fun edgesOf(vertex: V): Iterable<GraphEdge<V, E>> {
        return connected.getOrDefault(vertex, emptySet())
    }
}

