package com.app.quantitymeasurement.security;

// FIX: Removed duplicate CorsFilter bean.
// CORS is already fully configured in SecurityConfig.corsConfigurationSource().
// Having two conflicting CORS configurations (CorsFilter bean + Spring Security CORS)
// caused CORS preflight requests to fail or behave inconsistently.
// This class is intentionally left empty — SecurityConfig handles CORS.
