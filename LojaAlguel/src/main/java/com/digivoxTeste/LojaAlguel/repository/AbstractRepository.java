package com.digivoxTeste.LojaAlguel.repository;

import com.digivoxTeste.LojaAlguel.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
}
