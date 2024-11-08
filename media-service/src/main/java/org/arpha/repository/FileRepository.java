package org.arpha.repository;

import org.arpha.dto.media.enums.TargetType;
import org.arpha.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long>, QuerydslPredicateExecutor<File> {

    void deleteByTargetIdAndTargetType(long id, TargetType targetType);

    File findByTargetIdAndTargetType(long id, TargetType targetType);
    List<File> findAllByTargetIdAndTargetType(long id, TargetType targetType);
}
