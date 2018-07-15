package com.huawei.obs.silent.transfer.download;

import com.obs.services.ObsClient;
import com.obs.services.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

public class ObjectDownload {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectDownload.class);


    public static List<String> getObjectKeyList(ObsClient obsClient, String bucketName) {
        ObjectListing objectListing = null;
        objectListing = obsClient.listObjects(bucketName);
        List<String> objectKeyList = null;
        for (ObsObject object : objectListing.getObjects()) {
            objectKeyList.add(object.getObjectKey());
            object.getMetadata();
        }
        return objectKeyList;
    }

    public static List<ObjectMetadata> getObjectMetaList(ObsClient obsClient, String bucketName) {
        ObjectListing objectListing = null;
        objectListing = obsClient.listObjects(bucketName);
        List<ObjectMetadata> objectMataList = null;
        for (ObsObject object : objectListing.getObjects()) {
            objectMataList.add(object.getMetadata());
            return objectMataList;
        }
        return objectMataList;
    }

    public static BucketMetadataInfoRequest getBucketMeta(ObsClient obsClient, String bucketName) {
        BucketMetadataInfoRequest request = new BucketMetadataInfoRequest(bucketName);
        BucketMetadataInfoResult result = obsClient.getBucketMetadata(request);
        return request;
    }

    public static void transfer(ObsClient source, ObsClient target, String bucketName, String objectKey) {
        S3Object s3Object = source.getObject(bucketName, objectKey, null);
        InputStream inputStream = s3Object.getObjectContent();
        if (target.headBucket(bucketName)) {
            LOGGER.info("Bucket:" + bucketName + " already exists.");
        } else {
            LOGGER.info("Bucket does not exist, create bucket：" + bucketName + ".");
            target.createBucket(bucketName);
        }
        target.putObject(bucketName, objectKey, inputStream, null);
    }
}
