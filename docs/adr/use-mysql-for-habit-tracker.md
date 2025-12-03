# ADR Using MySQL Relational Database for Habit Tracker

## Date
2025.12.02

## Status
Proposed

## Context
We are building a habit tracker application that requires persistent storage for user data, habits, completions, and streak calculations.
The OpenAPI specification defines several possible entities and relationships:

1. User: Profile data and user information
2. Habit: User-created habits with metadata (name, description, frequency)
3. Habit Completion: Daily/weekly tracking of a habit
4. Streak: Data for user motivation and statistics

## Decision
We will use MySQL as our primary relational database management system for the following reasons:

### 1. Data Structure Suitability
The application has a clear relational data:
* One-to-many: User -> Habits
* One-to-many: Habit -> Completions
* One-to-one: Habit -> Streak
### 2. ACID Compliance Requirements
* Completion record and streak update must be atomic
* User and all associated habits + completions must be deleted together
* Referential integrity - no completions without a habit, no habit without a user
* Two completions for the same habit/day must not create duplicates
### 3. Experience and Ecosystem
* Strong tooling for migrations
* Robust ORM support
* Widely available expertise
* Possibility for cloud integration

## Consequences
### Positive
1. Foreign key enforcement
2. Native JOIN operations
3. Mature ecosystem
4. Personal familiarity
### Negative
1. Schema rigidity
2. Horizontal scaling more complex than NoSQL alternatives

## Alternatives Considered
### NoSQL(MongoDB)
* Pros: Flexible schemas, direct JSON mapping
* Cons: Complex join emulation