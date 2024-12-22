package az.orient.eshop.mapper;


import az.orient.eshop.entity.*;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperHelper {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final ProductRepository productRepository;
    private final ShelfRepository shelfRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final WarehouseRepository warehouseRepository;

    @Named("mapCategory")
    public Category mapCategory(Long categoryId) {
        return categoryRepository.findByIdAndActive(categoryId, EnumAvailableStatus.ACTIVE.getValue());
    }
    @Named("mapBrand")
    public Brand getBrand(Long brandId){
        return brandRepository.findByIdAndActive(brandId,EnumAvailableStatus.ACTIVE.getValue());
    }
    @Named("mapSubcategory")
    public Subcategory getSubcategory(Long subcategoryId){
        return subcategoryRepository.findSubcategoryByIdAndActive(subcategoryId,EnumAvailableStatus.ACTIVE.getValue());
    }
    @Named("mapSize")
    public Size getSize(Long sizeId){
        return sizeRepository.findSizeByIdAndActive(sizeId,EnumAvailableStatus.ACTIVE.getValue());
    }
    @Named("mapColor")
    public Color getColor(Long colorId){
        return colorRepository.findByIdAndActive(colorId,EnumAvailableStatus.ACTIVE.getValue());
    }
    @Named("mapProduct")
    public Product getProduct(Long productId){
        return productRepository.findProductByIdAndActive(productId,EnumAvailableStatus.ACTIVE.getValue());
    }
    @Named("mapShelf")
    public Shelf getShelf(Long shelfId){
        return shelfRepository.findByIdAndActive(shelfId,EnumAvailableStatus.ACTIVE.getValue());
    }
    @Named("mapProductDetails")
    public ProductDetails getProductDetails(Long productDetailsId){
        return productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
    }
    @Named("mapWarehouse")
    public Warehouse getWarehouse(Long warehouseId){
        return warehouseRepository.findWarehouseByIdAndActive(warehouseId,EnumAvailableStatus.ACTIVE.getValue());
    }
}
