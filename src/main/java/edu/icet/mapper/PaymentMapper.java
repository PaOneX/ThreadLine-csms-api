package edu.icet.mapper;

import edu.icet.model.dto.PaymentDto;
import edu.icet.model.dto.PaymentRequestDto;
import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Payment toEntity(PaymentRequestDto requestDto);

    @Mapping(target = "invoiceId", source = "invoice.id")
    @Mapping(target = "invoiceNumber", source = "invoice.invoiceNumber")
    PaymentDto toDto(Payment payment);

    List<PaymentDto> toDtoList(List<Payment> paymentEntities);

    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(PaymentRequestDto dto, @MappingTarget Payment entity);

    default Invoice mapInvoice(Long invoiceId) {
        if (invoiceId == null) {
            return null;
        }
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);
        return invoice;
    }
}
