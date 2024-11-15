package com.aehs.sge.exception

/**
 * This exception should be thrown a user tries to perform an operation but passes bad parameters.
 * This should be used to represent an  400 http response.
 */
class BadRequestException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}