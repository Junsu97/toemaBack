package junsu.personal.repository;

import jakarta.transaction.Transactional;
import junsu.personal.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    ImageEntity findByImageUrl(String fileName);
    List<ImageEntity> findByBoardNumber(Long boardNumber);

    @Transactional
    void deleteByBoardNumber(Long boardNumber);
}
