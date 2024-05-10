package com.trustprice.web.domain.file.repository

import com.trustprice.web.domain.file.entity.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FileRepository : JpaRepository<File, UUID>
