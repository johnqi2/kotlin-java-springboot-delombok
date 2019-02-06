package com.example.ktboot.model

import org.springframework.data.domain.AuditorAware
import java.util.Optional
import java.util.Random

class AuditorAwareImpl : AuditorAware<String> {
    private val auditors = listOf("john", "bryan", "arun")

    override fun getCurrentAuditor(): Optional<String> =
        Optional.of(auditors[Random().nextInt(auditors.size)])
}