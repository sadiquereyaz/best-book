package com.nabssam.bestbook.di

import javax.inject.Qualifier

/*Qualifiers are used in Dagger Hilt to distinguish between multiple bindings of the same type.
Auth: For the authenticated OkHttpClient.
UnAuth: For the unauthenticated OkHttpClient.
Track: For tracking-related Retrofit or other components.*/

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Auth

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnAuth

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Delhivery

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Mock

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Pin
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Pdf
