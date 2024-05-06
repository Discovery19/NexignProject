package org.project.crm.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.project.crm.dto.ChangeTariffMsisdnBody;
import org.project.crm.dto.InfoAbonent;
import org.project.crm.dto.PayMsisdnBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface CrmApi {
    @Operation(summary = "Добавление нового абонента \"Ромашка\"", description = "Данный метод предназначен для добавления нового абонента оператора \"Ромашка\"", security = {
            @SecurityRequirement(name = "adminAuthentication")    }, tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый абонент успешно создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InfoAbonent.class))),

            @ApiResponse(responseCode = "4XX", description = "Ошибка на стороне клиента"),

            @ApiResponse(responseCode = "5XX", description = "Ошибка на стороне сервера") })
    @RequestMapping(value = "/admin/save",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<?> savePost(@Parameter(in = ParameterIn.DEFAULT, description = "Параметры нового абонента", required=true, schema=@Schema()) @Valid @RequestBody InfoAbonent body
    );
    @Operation(summary = "Изменение тарифа абонента оператора \"Ромашка\"", description = "Данный метод предназначен для изменения тарифа абонента оператора \"Ромашка\"", security = {
            @SecurityRequirement(name = "adminAuthentication")    }, tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тариф абонента успешно изменен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),

            @ApiResponse(responseCode = "4XX", description = "Ошибка на стороне клиента"),

            @ApiResponse(responseCode = "5XX", description = "Ошибка на стороне сервера") })
    @RequestMapping(value = "/admin/changeTariff/{msisdn}",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<?> changeTariffMsisdnPatch(@Parameter(in = ParameterIn.PATH, description = "Номер телефона абонента", required=true, schema=@Schema()) @PathVariable("msisdn") String msisdn
            , @Parameter(in = ParameterIn.DEFAULT, description = "Параметры нового тарифа абонента", required=true, schema=@Schema()) @Valid @RequestBody ChangeTariffMsisdnBody body
    );
    @Operation(summary = "Данные абонента оператора \"Ромашка\"", description = "Данный метод предназначен для просмотра данных абонента \"Ромашка\"", security = {
            @SecurityRequirement(name = "userAuthentication")    }, tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные по абоненту", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),

            @ApiResponse(responseCode = "4XX", description = "Ошибка на стороне клиента"),

            @ApiResponse(responseCode = "5XX", description = "Ошибка на стороне сервера") })
    @RequestMapping(value = "/{msisdn}",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<?> checkUser(@Parameter(in = ParameterIn.PATH, description = "Номер телефона абонента", required=true, schema=@Schema()) @PathVariable("msisdn") String msisdn);
    @Operation(summary = "Пополнение баланса абонента оператора \"Ромашка\"", description = "Данный метод предназначен для пополнения баланса абонента оператора \"Ромашка\"", security = {
            @SecurityRequirement(name = "userAuthentication")    }, tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Баланс абонента успешно пополнен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),

            @ApiResponse(responseCode = "4XX", description = "Ошибка на стороне клиента"),

            @ApiResponse(responseCode = "5XX", description = "Ошибка на стороне сервера") })
    @RequestMapping(value = "/{msisdn}/pay",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<?> payMsisdnPatch(@Parameter(in = ParameterIn.PATH, description = "Номер телефона абонента", required=true, schema=@Schema()) @PathVariable("msisdn") String msisdn
            , @Parameter(in = ParameterIn.DEFAULT, description = "Сумма для пополнения баланса", required=true, schema=@Schema()) @Valid @RequestBody PayMsisdnBody body
    );


}