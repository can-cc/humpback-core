package com.chenfangwei.humpback.storage

import com.chenfangwei.humpback.share.exception.EntityNotFoundException
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import java.time.Duration

@RestController
class StorageController(private val storageService: StorageService) {

    @RequestMapping(value = ["/image/{objectId}"], method = [RequestMethod.GET], produces = [MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE])
    fun getImage(@PathVariable objectId: String): ResponseEntity<ByteArray> {
        val input = storageService.receiveObject(objectId) ?: throw EntityNotFoundException()
        val headers = HttpHeaders()
        headers.cacheControl = CacheControl.maxAge(Duration.ofHours(24 * 30)).headerValue
        return ResponseEntity(input.readBytes(), headers, HttpStatus.OK)
    }
}