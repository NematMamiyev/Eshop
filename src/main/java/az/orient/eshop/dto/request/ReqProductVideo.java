package az.orient.eshop.dto.request;

import lombok.Data;

@Data
public class ReqProductVideo {
    private Long id;
    private byte[] data;
    private String fileName;
    private String fileType;
    private Long productDetailsId;
}
