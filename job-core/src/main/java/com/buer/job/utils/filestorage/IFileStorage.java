package com.buer.job.utils.filestorage;

public interface IFileStorage {

  String getFileDownloadUrl(String key);

  byte[] downloadFileWithKeyOrThrowException(String key);

  String uploadFile(byte[] bytes, String filename, FileType fileType);
}
