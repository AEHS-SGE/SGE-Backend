package com.aehs.sge.exception

/**
 * This exception should be thrown a user tries to create a resource with an identifier that already exists.
 * This should be used to represent an  400 http response.
 */
class DuplicatedException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}