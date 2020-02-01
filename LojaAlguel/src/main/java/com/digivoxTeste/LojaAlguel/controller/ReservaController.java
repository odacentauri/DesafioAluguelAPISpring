package com.digivoxTeste.LojaAlguel.controller;

import com.digivoxTeste.LojaAlguel.exception.CRUDException;
import com.digivoxTeste.LojaAlguel.exception.ValidacaoException;
import com.digivoxTeste.LojaAlguel.model.Reserva;
import com.digivoxTeste.LojaAlguel.service.ReservaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(description = "Controlador responsável pelos serviços de Reserva")
@RestController
@RequestMapping("/api/reserva")
public class ReservaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    private ReservaService reservaService;

    @ApiOperation(value = "Insere um novo reserva na base de dados.", response = Reserva.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Object> insert(
            @ApiParam(value = "Objeto Reserva com as informações que serão cadastradas na base de dados. ", required = true)
            @RequestBody Reserva reserva) {
        try {
            return new ResponseEntity<>(reservaService.insert(reserva), HttpStatus.OK);
        } catch (ValidacaoException | CRUDException e) {
            return new ResponseEntity<>(e, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ApiOperation(value = "Atualiza as informações de um reserva na base de dados.", response = Reserva.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(
            @ApiParam(value = "Objeto Reserva com as informações que serão atualizadas na base de dados. ", required = true)
            @RequestBody Reserva reserva) {
        try {
            return new ResponseEntity<>(reservaService.update(reserva), HttpStatus.OK);
        } catch (ValidacaoException | CRUDException e) {
            return new ResponseEntity<>(e, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ApiOperation(value = "Recupera um reserva na base de dados, através do ID.", response = Reserva.class)
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(
            @ApiParam(value = "ID do cliente cadastrado na base de dados. ", required = true)
            @RequestParam("id") Long id) {
        Optional<Reserva> optionalReserva = reservaService.findById(id);
        if (optionalReserva.isPresent()){
            return new ResponseEntity<>(optionalReserva.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.PRECONDITION_FAILED);
    }

    @ApiOperation(value = "Lista os reservas cadastrados na base de dados.", response = Reserva.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getAll(){
        List<Reserva> optionalReserva = reservaService.getAll();
        if (!optionalReserva.isEmpty()){
            return new ResponseEntity(optionalReserva, HttpStatus.OK);
        }
        return new ResponseEntity(Boolean.FALSE, HttpStatus.PRECONDITION_FAILED);
    }

    @ApiOperation(value = "Remove um reserva cadastrado na base de dados.", response = Boolean.class)
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<Object> remove(
            @ApiParam(value = "Objeto reserva cadastrado na base de dados.", required = true)
            @RequestBody Reserva reserva) throws CRUDException {
        reservaService.remove(reserva);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @ApiOperation(value = "Remove um reserva cadastrado na base de dados, através do ID.", response = Boolean.class)
    @RequestMapping(value = "/removeById", method = RequestMethod.POST)
    public ResponseEntity<Object> removeById(
            @ApiParam(value = "ID do reserva a ser excluido na base de dados.", required = true)
            @RequestParam("id") Long id) throws CRUDException {
        reservaService.removeById(id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
