# ADR Using Lettuce as Redis Client for Java

## Date
2026.01.16

## Status
Accepted

## Context
The application requires a Redis client for caching operations to improve performance and reduce database load. 
I evaluated three popular Redis client libraries for Java/Spring applications:

1. Lettuce: Advanced Redis client supporting synchronous, asynchronous, and reactive API
2. Jedis: Simple, blocking Redis client
3. Redisson: Distributed and scalable Redis Java client with many features

## Decision
I have selected Lettuce as the Redis client library for the following reasons:

### 1. Thread Safety
*  Thread-safe by design, connections can be shared across multiple threads
### 2. Spring Boot Integration
* Lettuce is the default client in Spring Boot 2.x and 3.x
### 3. Performance and Scalability
* Lettuce uses Netty for network operations, providing non-blocking I/O and better performance under high concurrency

## Consequences
### Positive
1. Simpler configuration through Spring Boot auto-configuration
2. Better performance for concurrent user access patterns
3. Easy scaling to Redis Cluster when needed
### Negative
1. Dependency on Netty adds complexity to dependency tree
2. Less familiar

## Alternatives Considered
### Jedis
* Pros: Simpler API, easier to debug
* Cons: Not thread-safe (requires careful pooling), connection-per-thread model less efficient
### Redisson
* Pros: Rich feature set (distributed locks, queues, maps), built-in support for many distributed patterns
* Cons: More complex configuration