package com.digivoxTeste.LojaAlguel.controller;

import com.digivoxTeste.LojaAlguel.exception.CRUDException;
import com.digivoxTeste.LojaAlguel.exception.ValidacaoException;
import com.digivoxTeste.LojaAlguel.model.TipoItem;
import com.digivoxTeste.LojaAlguel.service.TipoItemService;
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

@Api(description = "Controlador responsável pelos serviços de Tipo Item")
@RestController
@RequestMapping("/api/tipoItem")
public class TipoItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TipoItemController.class);

    @Autowired
    private TipoItemService tipoItemService;

    @ApiOperation(value = "Insere um novo tipo de item na base de dados.", response = TipoItem.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Object> insert(
            @ApiParam(value = "Objeto Tipo Item com as informações que serão cadastradas na base de dados. ", required = true)
            @RequestBody TipoItem tipoItem) {
        try {
            return new ResponseEntity<>(tipoItemService.insert(tipoItem), HttpStatus.OK);
        } catch (ValidacaoException | CRUDException e) {
            return new ResponseEntity<>(e, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ApiOperation(value = "Atualiza as informações de um tipo de item na base de dados.", response = TipoItem.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(
            @ApiParam(value = "Objeto Tipo Item com as informações que serão atualizadas na base de dados. ", required = true)
            @RequestBody TipoItem tipoItem) {
        try {
            return new ResponseEntity<>(tipoItemService.update(tipoItem), HttpStatus.OK);
        } catch (ValidacaoException | CRUDException e) {
            return new ResponseEntity<>(e, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ApiOperation(value = "Recupera um tipo de item na base de dados, através do ID.", response = TipoItem.class)
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(
            @ApiParam(value = "ID do cliente cadastrado na base de dados. ", required = true)
            @RequestParam("id") Long id) {
        Optional<TipoItem> optionalTipoItem = tipoItemService.findById(id);
        if (optionalTipoItem.isPresent()){
            return new ResponseEntity<>(optionalTipoItem.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.PRECONDITION_FAILED);
    }

    @ApiOperation(value = "Lista os tipo item cadastrados na base de dados.", response = TipoItem.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getAll(){
        List<TipoItem> optionalTipoItem = tipoItemService.getAll();
        if (!optionalTipoItem.isEmpty()){
            return new ResponseEntity(optionalTipoItem, HttpStatus.OK);
        }
        return new ResponseEntity(Boolean.FALSE, HttpStatus.PRECONDITION_FAILED);
    }

    @ApiOperation(value = "Remove um tipo item cadastrado na base de dados.", response = Boolean.class)
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<Object> remove(
            @ApiParam(value = "Objeto tipo item cadastrado na base de dados.", required = true)
            @RequestBody TipoItem tipoItem) throws CRUDException {
        tipoItemService.remove(tipoItem);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @ApiOperation(value = "Remove um tipo item cadastrado na base de dados, através do ID.", response = Boolean.class)
    @RequestMapping(value = "/removeById", method = RequestMethod.POST)
    public ResponseEntity<Object> removeById(
            @ApiParam(value = "ID do tipo item a ser excluido na base de dados.", required = true)
            @RequestParam("id") Long id) throws CRUDException {
        tipoItemService.removeById(id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
