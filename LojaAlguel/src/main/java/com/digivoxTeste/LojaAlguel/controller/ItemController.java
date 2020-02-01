package com.digivoxTeste.LojaAlguel.controller;

import com.digivoxTeste.LojaAlguel.exception.CRUDException;
import com.digivoxTeste.LojaAlguel.exception.ValidacaoException;
import com.digivoxTeste.LojaAlguel.model.Item;
import com.digivoxTeste.LojaAlguel.service.ItemService;
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

@Api(description = "Controlador responsável pelos serviços de Item")
@RestController
@RequestMapping("/api/item")
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "Insere um novo item na base de dados.", response = Item.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Object> insert(
            @ApiParam(value = "Objeto Item com as informações que serão cadastradas na base de dados. ", required = true)
            @RequestBody Item item) {
        try {
            return new ResponseEntity<>(itemService.insert(item), HttpStatus.OK);
        } catch (ValidacaoException | CRUDException e) {
            return new ResponseEntity<>(e, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ApiOperation(value = "Atualiza as informações de um item na base de dados.", response = Item.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(
            @ApiParam(value = "Objeto Item com as informações que serão atualizadas na base de dados. ", required = true)
            @RequestBody Item item) {
        try {
            return new ResponseEntity<>(itemService.update(item), HttpStatus.OK);
        } catch (ValidacaoException | CRUDException e) {
            return new ResponseEntity<>(e, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ApiOperation(value = "Recupera um item na base de dados, através do ID.", response = Item.class)
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(
            @ApiParam(value = "ID do cliente cadastrado na base de dados. ", required = true)
            @RequestParam("id") Long id) {
        Optional<Item> optionalItem = itemService.findById(id);
        if (optionalItem.isPresent()){
            return new ResponseEntity<>(optionalItem.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.PRECONDITION_FAILED);
    }

    @ApiOperation(value = "Lista os itens cadastrados na base de dados.", response = Item.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getAll(){
        List<Item> optionalItem = itemService.getAll();
        if (!optionalItem.isEmpty()){
            return new ResponseEntity(optionalItem, HttpStatus.OK);
        }
        return new ResponseEntity(Boolean.FALSE, HttpStatus.PRECONDITION_FAILED);
    }

    @ApiOperation(value = "Remove um item cadastrado na base de dados.", response = Boolean.class)
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<Object> remove(
            @ApiParam(value = "Objeto item cadastrado na base de dados.", required = true)
            @RequestBody Item item) throws CRUDException {
        itemService.remove(item);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @ApiOperation(value = "Remove um item cadastrado na base de dados, através do ID.", response = Boolean.class)
    @RequestMapping(value = "/removeById", method = RequestMethod.POST)
    public ResponseEntity<Object> removeById(
            @ApiParam(value = "ID do item a ser excluido na base de dados.", required = true)
            @RequestParam("id") Long id) throws CRUDException {
        itemService.removeById(id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
