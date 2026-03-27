# Spring Mutable Fields Scanner

## Overview

Spring Mutable Fields Scanner is a lightweight tool designed to enforce statelessness in Spring Singleton beans by detecting and preventing the use of mutable fields. This helps avoid subtle thread-safety and concurrency bugs in your application.

## How It Works

The scanner inspector checks all singleton beans residing in your configured base packages during application startup. For each bean, it evaluates all declared fields. A field is considered safe and allowed only if it meets at least one of the following criteria:

1. **Immutable Modifiers**: The field is marked as `final` or `static`.
2. **Spring Injections**: The field is annotated with `@Autowired` or `@Value`.
3. **Singleton Beans**: The field type itself corresponds to a singleton bean in the application context.
4. **Explicitly Allowed**: The field is manually annotated with `@AllowMutable`.

If a field does not meet any of these rules, the scanner will halt the application startup securely and throw a `MutableFieldNotAllowedException`.

## Setup

1. Add the project to your build system's dependencies (e.g., Gradle or Maven).
2. Register the `MutableScannerPostProcessor` as a bean in your Spring configuration.
3. Pass your application's base packages to the `MutableScannerPostProcessor` constructor.

## Bypassing the Scanner (`@AllowMutable`)

For legitimate cases where mutable fields are actually needed inside a singleton (e.g., counters, caches, or concurrency-safe collections), you can simply use the `@AllowMutable` annotation on the specific field. This explicitly tells the scanner to bypass the mutability checks for that field.
