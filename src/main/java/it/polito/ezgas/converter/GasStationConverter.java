package it.polito.ezgas.converter;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.converter.UserConverter;

public class GasStationConverter {

    public GasStationConverter() {};

    public static GasStationDto convertEntityToDto(GasStation gs){
        GasStationDto gsDTO = new GasStationDto();
        gsDTO.setGasStationId(gs.getGasStationId());
        gsDTO.setGasStationName(gs.getGasStationName());
        gsDTO.setGasStationAddress(gs.getGasStationAddress());
        if(gs.getHasDiesel()) {
            gsDTO.setHasDiesel(gs.getHasDiesel());
            gsDTO.setDieselPrice(gs.getDieselPrice());
        }
        if(gs.getHasGas()){
            gsDTO.setHasGas(gs.getHasGas());
            gsDTO.setGasPrice(gs.getGasPrice());
        }
        if(gs.getHasSuper()){
            gsDTO.setHasSuper(gs.getHasSuper());
            gsDTO.setSuperPrice(gs.getSuperPrice());
        }
        if(gs.getHasSuperPlus()){
            gsDTO.setHasSuperPlus(gs.getHasSuperPlus());
            gsDTO.setSuperPlusPrice(gs.getSuperPlusPrice());
        }
        if(gs.getHasMethane()){
            gsDTO.setHasMethane(gs.getHasMethane());
            gsDTO.setMethanePrice(gs.getMethanePrice());
        }
        if(gs.getHasPremiumDiesel()){
            gsDTO.setHasPremiumDiesel(gs.getHasPremiumDiesel());
            gsDTO.setPremiumDieselPrice(gs.getPremiumDieselPrice());
        }
        gsDTO.setCarSharing(gs.getCarSharing());
        gsDTO.setLat(gs.getLat());
        gsDTO.setLon(gs.getLon());

        gsDTO.setReportUser(gs.getReportUser());

        if(gs.getUser() != null){
            gsDTO.setUserDto(UserConverter.convertEntityToDto(gs.getUser()));
        }

        gsDTO.setReportTimestamp(gs.getReportTimestamp());
        gsDTO.setReportDependability(gs.getReportDependability());
        return gsDTO;
    }

    public static GasStation convertDtoToEntity(GasStationDto gsDTO){
        GasStation gs = new GasStation();
        gs.setGasStationId(gsDTO.getGasStationId());
        gs.setGasStationName(gsDTO.getGasStationName());
        gs.setGasStationAddress(gsDTO.getGasStationAddress());
        if(gsDTO.getHasDiesel()) {
            gs.setHasDiesel(gsDTO.getHasDiesel());
            gs.setDieselPrice(gsDTO.getDieselPrice());
        }
        if(gsDTO.getHasGas()){
            gs.setHasGas(gsDTO.getHasGas());
            gs.setGasPrice(gsDTO.getGasPrice());
        }
        if(gsDTO.getHasSuper()){
            gs.setHasSuper(gsDTO.getHasSuper());
            gs.setSuperPrice(gsDTO.getSuperPrice());
        }
        if(gsDTO.getHasSuperPlus()){
            gs.setHasSuperPlus(gsDTO.getHasSuperPlus());
            gs.setSuperPlusPrice(gsDTO.getSuperPlusPrice());
        }
        if(gsDTO.getHasMethane()){
            gs.setHasMethane(gsDTO.getHasMethane());
            gs.setMethanePrice(gsDTO.getMethanePrice());
        }
        if(gsDTO.getHasPremiumDiesel()){
            gs.setHasPremiumDiesel(gsDTO.getHasPremiumDiesel());
            gs.setPremiumDieselPrice(gsDTO.getPremiumDieselPrice());
        }
        gs.setCarSharing(gsDTO.getCarSharing());
        gs.setLat(gsDTO.getLat());
        gs.setLon(gsDTO.getLon());

        gs.setReportUser(gsDTO.getReportUser());

        if(gsDTO.getUserDto() != null){
            gs.setUser(UserConverter.convertDtoToEntity(gsDTO.getUserDto()));
        }

        gs.setReportTimestamp(gsDTO.getReportTimestamp());
        gs.setReportDependability(gsDTO.getReportDependability());
        return gs;
    }

}
