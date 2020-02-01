package com.digivoxTeste.LojaAlguel.service;

import com.digivoxTeste.LojaAlguel.exception.ReservaException;
import com.digivoxTeste.LojaAlguel.exception.CRUDException;
import com.digivoxTeste.LojaAlguel.exception.GlobalException;
import com.digivoxTeste.LojaAlguel.exception.ValidacaoException;
import com.digivoxTeste.LojaAlguel.model.Reserva;
import com.digivoxTeste.LojaAlguel.repository.ReservaRepository;
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
public class ReservaService extends GenericService<Reserva>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Override
    @Transactional
    public Reserva insert(Reserva reserva) throws CRUDException, ValidacaoException {

        Optional<ReservaException> optReservaException = validarReserva(reserva);

        if (optReservaException.isPresent()){
            LOGGER.error("{}", optReservaException.get());

            throw new ReservaException(optReservaException.get());
        }

        try {
            Reserva reservaDB = reservaRepository.save(reserva);

            reservaDB = reservaRepository.save(reservaDB);
            return reservaDB;
        } catch (ConstraintViolationException exception) {
            GlobalException globalException = ExceptionUtils.montarConstraintViolationException(exception, Reserva.class.getSimpleName(), getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());

            LOGGER.error(globalException.toString());

            throw new ValidacaoException(globalException);
        }
    }

    @Override
    @Transactional
    public Reserva update(Reserva reserva) throws CRUDException, ValidacaoException {

        Optional<ReservaException> optionalReservaException = validarReserva(reserva);

        if (optionalReservaException.isPresent()){
            LOGGER.error("{}", optionalReservaException.get());

            throw new ReservaException(optionalReservaException.get());
        }

        try {
            return reservaRepository.save(reserva);
        } catch (ConstraintViolationException exception) {
            GlobalException globalException = ExceptionUtils.montarConstraintViolationException(exception,
                    Reserva.class.getSimpleName(), getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());

            LOGGER.error("{}", globalException);
            throw new ValidacaoException(globalException);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reserva> findById(Long id) {
        LOGGER.debug("id: {}", id);

        Optional<Reserva> optionalReserva = reservaRepository.findById(id);

        optionalReserva.ifPresent(reserva -> LOGGER.debug("reserva: {}", reserva));

        return optionalReserva;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> getAll() {
        return reservaRepository.findAll();
    }

    @Override
    @Transactional
    public void remove(Reserva reserva) throws CRUDException {

        if (super.exists(reserva)) {
            LOGGER.debug("reserva: {}", reserva);

            reservaRepository.delete(reserva);
        }
    }

    @Override
    @Transactional
    public void removeById(Long id) throws CRUDException {

        if (super.exists(id)) {
            LOGGER.debug("idReserva: {}", id);

            Optional<Reserva> optionalReserva = findById(id);

            if (optionalReserva.isPresent()){
                Reserva reserva = optionalReserva.get();

                LOGGER.debug("reserva: {}", reserva);

                reservaRepository.deleteById(id);
            }
        }
    }

    private Optional<ReservaException> validarReserva(Reserva reserva) {

        ReservaException reservaException = new ReservaException();

        reservaException.setEntidade(Reserva.class.getSimpleName());
        reservaException.setServico(getClass().getSimpleName());
        reservaException.setMetodo(Thread.currentThread().getStackTrace()[1].getMethodName());

        LOGGER.debug("Reserva: {}", reserva);

        Optional<ReservaException> optException = this.validarReservaInformado(reserva);

        if (optException.isPresent()) {
            reservaException.addAlvos(optException.get().getAlvos());
            reservaException.addDescricoes(optException.get().getDescricoes());
        }

        if (super.exists(reserva)){
            reservaException.addAlvo(ErrorConstants.RESERVA_JA_CADASTRADO);
            reservaException.addDescricao(ErrorConstants.RESERVA_JA_CADASTRADO_DESCRICAO);
        }

        if (!reservaException.getAlvos().isEmpty()){
            return Optional.of(reservaException);
        }

        return Optional.empty();
    }

    private Optional<ReservaException> validarReservaInformado(Reserva reserva){

        ReservaException reservaException = new ReservaException();

        if (Objects.isNull(reserva)) {
            reservaException.addAlvo(ErrorConstants.RESERVA_NAO_INFORMADO);
            reservaException.addDescricao(ErrorConstants.RESERVA_NAO_INFORMADO_DESCRICAO);

            return Optional.of(reservaException);
        }

        if (!itemRepository.findById(reserva.getItemReservado().getId()).isPresent()){
            reservaException.addAlvo(ErrorConstants.ITEM_NAO_EXISTE);
            reservaException.addDescricao(ErrorConstants.ITEM_NAO_EXISTE_DESCRICAO);

            return Optional.of(reservaException);
        }

        if (!reservaException.getAlvos().isEmpty()){
            return Optional.of(reservaException);
        }

        return Optional.empty();
    }

}
