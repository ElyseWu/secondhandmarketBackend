package team3.secondhand.service;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team3.secondhand.exception.GCSUploadException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ItemImageStorageService {

    @Value("secondhand-item-images")
    private String bucketName;

    private final Storage storage;


    public ItemImageStorageService(Storage storage) {
        this.storage = storage;
    }

    public String save(MultipartFile file) throws GCSUploadException {
        // 上传的文件名
        String filename = UUID.randomUUID().toString();
        // Blob的意思是binary large object
        // 这个东西其实就是在配置你存到的GCP bucket中文件的各种格式
        // 包括你存到哪个bucket，文件名是什么，格式是什么，谁能访问这个bucket的内容
        BlobInfo blobInfo = null;
        try {
            blobInfo = storage.createFrom(
                    BlobInfo
                            .newBuilder(bucketName, filename)
                            .setContentType("image/jpeg")
                            .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                            .build(),
                    file.getInputStream());
        } catch (IOException exception) {
            throw new GCSUploadException("Failed to upload file to GCS");
        }
        // 上传成功后，我们会返回这个image存在gcp中的url
        return blobInfo.getMediaLink();
    }

}
