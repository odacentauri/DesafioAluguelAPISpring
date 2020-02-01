package com.digivoxTeste.LojaAlguel.service;

import com.digivoxTeste.LojaAlguel.exception.*;
import com.digivoxTeste.LojaAlguel.model.Item;
import com.digivoxTeste.LojaAlguel.repository.ItemRepository;
import com.digivoxTeste.LojaAlguel.util.ErrorConstants;
import com.digivoxTeste.LojaAlguel.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService extends GenericService<Item> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TipoItemService tipoItemService;

    @Override
    @Transactional
    public Item insert(Item item) throws CRUDException, ValidacaoException {

        Optional<ItemException> optItemException = validarItem(item);

        if (optItemException.isPresent()){
            LOGGER.error("{}", optItemException.get());

            throw new ItemException(optItemException.get());
        }

        try {
            Item itemDB = itemRepository.save(item);

            itemDB = itemRepository.save(itemDB);

            return itemDB;
        } catch (ConstraintViolationException exception) {
            GlobalException globalException = ExceptionUtils.montarConstraintViolationException(exception, Item.class.getSimpleName(), getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());

            LOGGER.error(globalException.toString());

            throw new ValidacaoException(globalException);
        }
    }

    @Override
    @Transactional
    public Item update(Item item) throws CRUDException, ValidacaoException {

        Optional<ItemException> optionalItemException = validarItem(item);

        if (optionalItemException.isPresent()){
            LOGGER.error("{}", optionalItemException.get());

            throw new ItemException(optionalItemException.get());
        }

        Optional<TipoItemException> optionalTipoItemException = tipoItemService.validarTipoItem(item.getTipoItem());

        if (optionalTipoItemException.isPresent()){
            LOGGER.error("{}", optionalTipoItemException.get());

            throw new TipoItemException(optionalTipoItemException.get());
        }

        try {
            return itemRepository.save(item);
        } catch (ConstraintViolationException exception) {
            GlobalException globalException = ExceptionUtils.montarConstraintViolationException(exception,
                    Item.class.getSimpleName(), getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());

            LOGGER.error("{}", globalException);
            throw new ValidacaoException(globalException);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Item> findById(Long id) {
        LOGGER.debug("id: {}", id);

        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            LOGGER.debug("item: {}", optionalItem.get());
        }

        return optionalItem;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    @Transactional
    public void remove(Item item) throws CRUDException {

        if (super.exists(item)) {
            LOGGER.debug("item: {}", item);

            itemRepository.delete(item);
        }
    }

    @Override
    @Transactional
    public void removeById(Long id) throws CRUDException {

        if (super.exists(id)) {
            LOGGER.debug("idItem: {}", id);

            Optional<Item> optionalItem = findById(id);

            if (optionalItem.isPresent()){
                Item item = optionalItem.get();

                LOGGER.debug("item: {}", item);

                itemRepository.deleteById(id);
            }
        }
    }

    private Optional<ItemException> validarItem(Item item) {

        ItemException itemException = new ItemException();

        itemException.setEntidade(Item.class.getSimpleName());
        itemException.setServico(getClass().getSimpleName());
        itemException.setMetodo(Thread.currentThread().getStackTrace()[1].getMethodName());

        LOGGER.debug("Item: {}", item);

        Optional<ItemException> optException = this.validarItemInformado(item);

        if (optException.isPresent()) {
            itemException.addAlvos(optException.get().getAlvos());
            itemException.addDescricoes(optException.get().getDescricoes());
        }

        if (super.exists(item)){
            itemException.addAlvo(ErrorConstants.ITEM_JA_CADASTRADO);
            itemException.addDescricao(ErrorConstants.ITEM_JA_CADASTRADO_DESCRICAO);
        }

        Optional<TipoItemException> optionalTipoItemException = tipoItemService.validarTipoItem(item.getTipoItem());

        LOGGER.debug("optTipoItemException.isPresent(): {}", optionalTipoItemException.isPresent());

        if (optionalTipoItemException.isPresent()){
            itemException.addAlvos(optionalTipoItemException.get().getAlvos());
            itemException.addDescricoes(optionalTipoItemException.get().getDescricoes());
        }

        if (!itemException.getAlvos().isEmpty()){
            return Optional.of(itemException);
        }

        return Optional.empty();
    }

    private Optional<ItemException> validarItemInformado(Item item){

        ItemException itemException = new ItemException();

        if (Objects.isNull(item)) {
            itemException.addAlvo(ErrorConstants.ITEM_NAO_INFORMADO);
            itemException.addDescricao(ErrorConstants.ITEM_NAO_INFORMADO_DESCRICAO);

            return Optional.of(itemException);
        }

        if (!itemException.getAlvos().isEmpty()){
            return Optional.of(itemException);
        }

        return Optional.empty();
    }
}
