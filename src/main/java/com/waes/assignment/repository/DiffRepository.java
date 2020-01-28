package com.waes.assignment.repository;

import com.waes.assignment.entity.Diff;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository extends Spring Data Jpa Repository
 * All database operations for Diff entity
 *
 * @developer -- ilkercolakoglu
 */
public interface DiffRepository extends JpaRepository<Diff, Long> {
}
