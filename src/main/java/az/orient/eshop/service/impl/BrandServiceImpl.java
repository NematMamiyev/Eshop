package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqBrand;
import az.orient.eshop.dto.response.RespBrand;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Brand;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.BrandRepository;
import az.orient.eshop.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Response<RespBrand> addBrand(ReqBrand reqBrand) {
        Response<RespBrand> response = new Response<>();
        try {
            String name = reqBrand.getName();
            if (name == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            boolean uniqueName = brandRepository.existsBrandByNameAndActive(name,EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            Brand brand = Brand.builder()
                    .id(reqBrand.getId())
                    .name(name)
                    .build();
            brandRepository.save(brand);
            RespBrand respBrand = convert(brand);
            response.setT(respBrand);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<List<RespBrand>> brandList() {
        Response<List<RespBrand>> response = new Response<>();
        try {
            List<Brand> brandList = brandRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (brandList.isEmpty()) {
                throw new EshopException(ExceptionConstants.BRAND_NOT_FOUND, "Brand not found");
            }
            List<RespBrand> respBrandList = brandList.stream().map(this::convert).toList();
            response.setT(respBrandList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }

        return response;
    }

    @Override
    public Response<RespBrand> getBrandById(Long id) {
        Response<RespBrand> response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Brand brand = brandRepository.findByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (brand == null){
                throw new EshopException(ExceptionConstants.BRAND_NOT_FOUND,"Brand not found");
            }
            RespBrand respBrand = convert(brand);
            response.setT(respBrand);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespBrand> updateBrand(ReqBrand reqBrand) {
        Response<RespBrand> response = new Response<>();
        try {
            Long id = reqBrand.getId();
            String name = reqBrand.getName();
            if (id == null || name == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id or name is invalid");
            }
            Brand brand = brandRepository.findByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (brand == null){
                throw new EshopException(ExceptionConstants.BRAND_NOT_FOUND, "Brand not found");
            }
            boolean uniqueName = brandRepository.existsBrandByNameAndActiveAndIdNot(name,EnumAvailableStatus.ACTIVE.getValue(), id);
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            brand.setName(reqBrand.getName());
            brandRepository.save(brand);
            RespBrand respBrand = convert(brand);
            response.setT(respBrand);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response deleteBrand(Long id) {
        Response response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id not found");
            }
            Brand brand = brandRepository.findByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (brand == null){
                throw new EshopException(ExceptionConstants.BRAND_NOT_FOUND, "Brand not found");
            }
            brand.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            brandRepository.save(brand);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    private RespBrand convert(Brand brand) {
        return RespBrand.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }

}
