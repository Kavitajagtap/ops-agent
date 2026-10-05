INSERT INTO incident (title, severity, status, service, created_at) VALUES
('Checkout returns 500 errors', 'HIGH', 'OPEN', 'checkout-service', CURRENT_TIMESTAMP),
('Slow search responses', 'MEDIUM', 'OPEN', 'search-service', CURRENT_TIMESTAMP),
('Email delay resolved', 'LOW', 'RESOLVED', 'notification-service', CURRENT_TIMESTAMP);

INSERT INTO log_entry (service, level, message, timestamp) VALUES
('checkout-service', 'ERROR', 'NullPointerException in PaymentProcessor.charge', CURRENT_TIMESTAMP),
('checkout-service', 'ERROR', 'Connection timeout to payment-gateway after 30s', CURRENT_TIMESTAMP),
('checkout-service', 'WARN', 'Retrying payment-gateway call, attempt 3', CURRENT_TIMESTAMP),
('search-service', 'WARN', 'Query latency 4200ms exceeds threshold', CURRENT_TIMESTAMP),
('search-service', 'ERROR', 'Elasticsearch node unreachable: es-node-2', CURRENT_TIMESTAMP),
('notification-service', 'INFO', 'Email queue drained, backlog cleared', CURRENT_TIMESTAMP);