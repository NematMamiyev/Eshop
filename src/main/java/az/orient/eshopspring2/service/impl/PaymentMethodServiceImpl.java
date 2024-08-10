package az.orient.eshopspring2.service.impl;

import az.orient.eshopspring2.dto.request.ReqPaymentMethod;
import az.orient.eshopspring2.dto.response.RespPaymentMethod;
import az.orient.eshopspring2.dto.response.RespStatus;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.entity.PaymentMethod;
import az.orient.eshopspring2.enums.EnumAvailableStatus;
import az.orient.eshopspring2.exception.EshopException;
import az.orient.eshopspring2.exception.ExceptionConstants;
import az.orient.eshopspring2.repository.PaymentMethodRepository;
import az.orient.eshopspring2.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public Response<RespPaymentMethod> addPaymentMethod(ReqPaymentMethod reqPaymentMethod) {
        Response<RespPaymentMethod> response = new Response<>();
        try {
            String name = reqPaymentMethod.getName();
            if (name == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name not found");
            }
            boolean uniqueName = paymentMethodRepository.existsPaymentMethodByNameAndActive(name, EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueName) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            PaymentMethod paymentMethod = PaymentMethod.builder()
                    .name(name)
                    .build();
            paymentMethodRepository.save(paymentMethod);
            RespPaymentMethod respPaymentMethod = convert(paymentMethod);
            response.setT(respPaymentMethod);
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
    public Response<List<RespPaymentMethod>> getPaymentMethodList() {
        Response<List<RespPaymentMethod>> response = new Response<>();
        try {
            List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (paymentMethodList.isEmpty()) {
                throw new EshopException(ExceptionConstants.PAYMENT_METHOD_NOT_FOUND, "PaymentMethod not found");
            }
            List<RespPaymentMethod> respPaymentMethodList = paymentMethodList.stream().map(this::convert).toList();
            response.setT(respPaymentMethodList);
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
    public Response<RespPaymentMethod> getPaymentMethodById(Long id) {
        Response<RespPaymentMethod> response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            PaymentMethod paymentMethod = paymentMethodRepository.findPaymentMethodByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (paymentMethod == null) {
                throw new EshopException(ExceptionConstants.PAYMENT_METHOD_NOT_FOUND, " PaymentMethod not found");
            }
            RespPaymentMethod respPaymentMethod = convert(paymentMethod);
            response.setT(respPaymentMethod);
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
    public Response<RespPaymentMethod> updatePaymentMethod(ReqPaymentMethod reqPaymentMethod) {
        Response<RespPaymentMethod> response = new Response<>();
        try {
            Long id = reqPaymentMethod.getId();
            String name = reqPaymentMethod.getName();
            if (id == null || name == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            PaymentMethod paymentMethod = paymentMethodRepository.findPaymentMethodByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (paymentMethod == null) {
                throw new EshopException(ExceptionConstants.PAYMENT_METHOD_NOT_FOUND, "Payment method not found");
            }
            paymentMethod.setName(name);
            paymentMethodRepository.save(paymentMethod);
            RespPaymentMethod respPaymentMethod = convert(paymentMethod);
            response.setT(respPaymentMethod);
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
    public Response deletePaymentMethod(Long id) {
        Response response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            PaymentMethod paymentMethod = paymentMethodRepository.findPaymentMethodByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (paymentMethod == null){
                throw new EshopException(ExceptionConstants.PAYMENT_METHOD_NOT_FOUND, "Payment method not found");
            }
            paymentMethod.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            paymentMethodRepository.save(paymentMethod);
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

    private RespPaymentMethod convert(PaymentMethod paymentMethod) {
        return RespPaymentMethod.builder()
                .id(paymentMethod.getId())
                .name(paymentMethod.getName())
                .build();
    }
}
