package com.digivoxTeste.LojaAlguel.controller;

import com.digivoxTeste.LojaAlguel.exception.CRUDException;
import com.digivoxTeste.LojaAlguel.exception.ValidacaoException;
import com.digivoxTeste.LojaAlguel.model.Aluguel;
import com.digivoxTeste.LojaAlguel.service.AluguelService;
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

@Api(description = "Controlador responsável pelos serviços de Aluguel")
@RestController
@RequestMapping("/api/aluguel")
public class AluguelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AluguelController.class);

    @Autowired
    private AluguelService aluguelService;

    @ApiOperation(value = "Insere um novo aluguel na base de dados.", response = Aluguel.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Object> insert(
            @ApiParam(value = "Objeto Aluguel com as informações que serão cadastradas na base de dados. ", required = true)
            @RequestBody Aluguel aluguel) {
        try {
            return new ResponseEntity<>(aluguelService.insert(aluguel), HttpStatus.OK);
        } catch (ValidacaoException | CRUDException e) {
            return new ResponseEntity<>(e, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ApiOperation(value = "Atualiza as informações de um aluguel na base de dados.", response = Aluguel.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(
            @ApiParam(value = "Objeto Aluguel com as informações que serão atualizadas na base de dados. ", required = true)
            @RequestBody Aluguel aluguel) {
        try {
            return new ResponseEntity<>(aluguelService.update(aluguel), HttpStatus.OK);
        } catch (ValidacaoException | CRUDException e) {
            return new ResponseEntity<>(e, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ApiOperation(value = "Recupera um aluguel na base de dados, através do ID.", response = Aluguel.class)
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(
            @ApiParam(value = "ID do cliente cadastrado na base de dados. ", required = true)
            @RequestParam("id") Long id) {
        Optional<Aluguel> optionalAluguel = aluguelService.findById(id);
        if (optionalAluguel.isPresent()){
            return new ResponseEntity<>(optionalAluguel.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.PRECONDITION_FAILED);
    }

    @ApiOperation(value = "Lista os alugueis cadastrados na base de dados.", response = Aluguel.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getAll(){
        List<Aluguel> optionalAluguel = aluguelService.getAll();
        if (!optionalAluguel.isEmpty()){
            return new ResponseEntity(optionalAluguel, HttpStatus.OK);
        }
        return new ResponseEntity(Boolean.FALSE, HttpStatus.PRECONDITION_FAILED);
    }

    @ApiOperation(value = "Remove um aluguel cadastrado na base de dados.", response = Boolean.class)
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<Object> remove(
            @ApiParam(value = "Objeto aluguel cadastrado na base de dados.", required = true)
            @RequestBody Aluguel aluguel) throws CRUDException {
        aluguelService.remove(aluguel);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @ApiOperation(value = "Remove um aluguel cadastrado na base de dados, através do ID.", response = Boolean.class)
    @RequestMapping(value = "/removeById", method = RequestMethod.POST)
    public ResponseEntity<Object> removeById(
            @ApiParam(value = "ID do aluguel a ser excluido na base de dados.", required = true)
            @RequestParam("id") Long id) throws CRUDException {
        aluguelService.removeById(id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
