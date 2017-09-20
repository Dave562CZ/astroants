package cz.richter.david.astroants.shortestpath

import es.usc.citius.hipster.graph.*

/**
 * Copied and changed code from [es.usc.citius.hipster.graph.HashBasedHipsterDirectedGraph]
 * to use ConcurrentHashMap and used kotlin.Sequences instead of internal functions to implement lazy computation of elements
 */
class ConcurrentHashBasedHipsterDirectedGraph<V, E> : ConcurrentHashBasedHipsterGraph<V, E>(), HipsterMutableGraph<V, E>, HipsterDirectedGraph<V, E> {

    override fun buildEdge(v1: V, v2: V, value: E): GraphEdge<V, E> {
        return DirectedEdge(v1, v2, value)
    }

    override fun outgoingEdgesOf(vertex: V): Iterable<GraphEdge<V, E>> {
        return edgesOf(vertex).asSequence()
                .filter { it.vertex1 == vertex }
                .asIterable()
    }

    override fun incomingEdgesOf(vertex: V): Iterable<GraphEdge<V, E>> {
        return edgesOf(vertex).asSequence()
                .filter { it.vertex2 == vertex }
                .asIterable()
    }

    override fun edges(): Iterable<GraphEdge<V, E>> {
        return vedges().asSequence()
                .filter { it.key == it.value.vertex1 }
                .map { it.value }
                .asIterable()
    }
}