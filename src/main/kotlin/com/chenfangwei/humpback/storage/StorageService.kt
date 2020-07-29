package com.chenfangwei.humpback.storage

import io.minio.PutObjectArgs
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.MessageDigest
import java.util.UUID


@Service
class StorageService(
        private val minIOService: MinIOService,
        @Value("\${storage.minio.buckets.image}") private val imageBucketName: String) {


    fun saveObject(byteArrayInputStream: ByteArrayInputStream, contentType: String?): String {
        val objectName = UUID.randomUUID().toString().replace("-", "")
        minIOService.getMinioClient().putObject(
                     PutObjectArgs.builder().bucket(imageBucketName).`object`(objectName).stream(
                byteArrayInputStream, byteArrayInputStream.available().toLong(), -1)
                             .contentType(contentType)
               .build())
        return "object://$objectName"
    }

    fun receiveObject(objectId: String): InputStream? {
        return minIOService.getMinioClient().getObject(imageBucketName, objectId)
    }
}