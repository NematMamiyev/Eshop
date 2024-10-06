package az.orient.eshop.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class RespMedia {
    private Set<RespProductImage> respProductImages;
    private Set<RespProductVideo> respProductVideos;
}
