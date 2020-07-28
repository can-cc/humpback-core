package com.chenfangwei.humpback.storage

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MinIOService(@Value("\${storage.minio.accesskey}") accessKey: String, @Value("\${storage.minio.endpoint}") endpoint: String, @Value("\${storage.minio.secretkey}") secretKey: String, @Value("\${storage.minio.buckets.image}") imageBucketName: String) {
    private val minioClient: MinioClient = MinioClient.builder()
            .endpoint(endpoint)
            .credentials(accessKey, secretKey)
            .build()

    init {
        checkBucketExistOrCreate(imageBucketName)
    }

    fun getMinioClient(): MinioClient {
        return minioClient
    }

    private fun checkBucketExistOrCreate(bucket: String) {
        val isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build())
        }
    }
}