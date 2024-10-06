package az.orient.eshop.enums;

import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum MediaTypeEnum {
    IMAGE(0) , VIDEO(1);

    private int value;
    public static void fromValue(String mediaType) {
        for (MediaTypeEnum mediaTypeEnum : values()) {
            if (mediaTypeEnum.name().equals(mediaType)) {
                return;
            }
        }
        throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid value");
    }
}
