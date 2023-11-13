package com.phollux.tupropiedad.contract.repos;

import com.phollux.tupropiedad.clause.domain.Clause;
import com.phollux.tupropiedad.contract.domain.Contract;
import com.phollux.tupropiedad.image.domain.Image;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContractRepository extends JpaRepository<Contract, Integer> {

    Page<Contract> findAllById(Integer id, Pageable pageable);

    List<Contract> findAllByClauses(Clause clause);

    List<Contract> findAllByImages(Image image);

}
