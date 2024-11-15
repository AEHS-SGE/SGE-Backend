package com.aehs.sge.exception

/**
 * This exception should be thrown a user tries to perform an operation on a resource that does not exist..
 * This should be used to represent an  404 http response.
 */
class NotFoundException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}