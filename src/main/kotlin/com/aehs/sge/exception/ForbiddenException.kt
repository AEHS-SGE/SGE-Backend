package com.aehs.sge.exception

/**
 * This exception should be thrown a user tries to perform an operation that he is not allowed to.
 * This should be used to represent an  403 http response.
 */
class ForbiddenException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}