package edu.icet.mapper;

import edu.icet.model.dto.InvoiceDto;
import edu.icet.model.dto.InvoiceRequestDto;
import edu.icet.model.entity.InvoiceEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceEntity toEntity(InvoiceRequestDto requestDto);
    InvoiceDto toDto(InvoiceEntity dto);
    List<InvoiceDto> toDtoList(List<InvoiceEntity> entities);
}
