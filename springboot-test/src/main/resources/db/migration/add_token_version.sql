ALTER TABLE account ADD COLUMN token_version INT DEFAULT 0 COMMENT 'Token版本号，修改密码时递增使旧token失效';
UPDATE account SET token_version = 0 WHERE token_version IS NULL;
