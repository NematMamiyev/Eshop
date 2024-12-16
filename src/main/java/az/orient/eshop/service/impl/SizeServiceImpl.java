package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqSize;
import az.orient.eshop.dto.response.RespSize;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Size;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.SizeMapper;
import az.orient.eshop.repository.SizeRepository;
import az.orient.eshop.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    @Override
    public Response<RespSize> addSize(ReqSize reqSize) {
        Response<RespSize> response = new Response<>();
        try {
            String name = reqSize.getName();
            if (name == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name is null");
            }
            boolean uniqueName = sizeRepository.existsSizeByNameAndActive(name, EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueName) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            Size size = sizeMapper.toSize(reqSize);
            sizeRepository.save(size);
            RespSize respSize = sizeMapper.toRespSize(size);
            response.setT(respSize);
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
    public Response<List<RespSize>> getSizeList() {
        Response<List<RespSize>> response = new Response<>();
        try {
            List<Size> sizeList = sizeRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (sizeList.isEmpty()) {
                throw new EshopException(ExceptionConstants.SIZE_NOT_FOUND, "Size is empty");
            }
            List<RespSize> respSizeList = sizeMapper.toRespSizeList(sizeList);
            response.setT(respSizeList);
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
    public Response<RespSize> getSizeById(Long id) {
        Response<RespSize> response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            Size size = sizeRepository.findSizeByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (size == null){
                throw new EshopException(ExceptionConstants.SIZE_NOT_FOUND, "Size not found");
            }
            RespSize respSize = sizeMapper.toRespSize(size);
            response.setT(respSize);
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
    public Response<RespSize> updateSize(Long id, ReqSize reqSize) {
        Response<RespSize> response = new Response<>();
        try {
            String name = reqSize.getName();
            if (id == null || name == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id or name is null");
            }
            Size size = sizeRepository.findSizeByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (size == null){
                throw new EshopException(ExceptionConstants.SIZE_NOT_FOUND, "Size not found");
            }
            boolean uniqueSize = sizeRepository.existsSizeByNameAndActiveAndIdNot(name,EnumAvailableStatus.ACTIVE.getValue(), id);
            if (uniqueSize){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            sizeMapper.updateSizeFromReqSize(size,reqSize);
            sizeRepository.save(size);
            RespSize respSize = sizeMapper.toRespSize(size);
            response.setT(respSize);
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
    public Response deleteSize(Long id) {
        Response response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            Size size = sizeRepository.findSizeByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (size == null){
                throw new EshopException(ExceptionConstants.SIZE_NOT_FOUND, "Size not found");
            }
            size.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            sizeRepository.save(size);
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
}
