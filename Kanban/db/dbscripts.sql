-- ============================================
-- SCHEMA: KANBAN / RBAC SYSTEM
-- DATABASE: PostgreSQL
-- ============================================

-- ============================================
-- 1. USERS
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    domain VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);

-- ============================================
-- 2. ROLES
-- ============================================
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- ============================================
-- 3. USER_ROLES (Many-to-Many)
-- ============================================
CREATE TABLE IF NOT EXISTS user_roles (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id INT NOT NULL,

    CONSTRAINT unique_user_role UNIQUE (user_id, role_id),

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE
);

-- ============================================
-- VIEW: USER ROLE SUMMARY
-- ============================================
CREATE OR REPLACE VIEW user_roles_view AS
SELECT 
    u.full_name,
    u.email,
    r.name AS role
FROM user_roles ur
JOIN users u ON ur.user_id = u.id
JOIN roles r ON ur.role_id = r.id;

-- ============================================
-- 4. TEAM
-- ============================================
CREATE TABLE IF NOT EXISTS team (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_by BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_team_created_by
        FOREIGN KEY (created_by)
        REFERENCES users(id)
        ON DELETE SET NULL
);

-- ============================================
-- 5. TEAM MEMBERS
-- ============================================
CREATE TABLE IF NOT EXISTS team_members (
    id BIGSERIAL PRIMARY KEY,
    team_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT unique_team_user UNIQUE (team_id, user_id),

    CONSTRAINT fk_team_members_team
        FOREIGN KEY (team_id)
        REFERENCES team(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_team_members_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_team_members_team_id ON team_members(team_id);
CREATE INDEX idx_team_members_user_id ON team_members(user_id);

-- ============================================
-- 6. ITERATION (SPRINT)
-- ============================================
CREATE TABLE IF NOT EXISTS iteration (
    id BIGSERIAL PRIMARY KEY,
    iteration_number INT NOT NULL,
    name VARCHAR(50) NOT NULL,

    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,

    status VARCHAR(20) DEFAULT 'PLANNED'
        CHECK (status IN ('PLANNED', 'ACTIVE', 'COMPLETED')),

    team_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT unique_team_iteration UNIQUE (team_id, iteration_number),
    CONSTRAINT unique_team_iteration_name UNIQUE (team_id, name),

    CONSTRAINT fk_iteration_team
        FOREIGN KEY (team_id)
        REFERENCES team(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_iteration_team_id ON iteration(team_id);

-- ============================================
-- 7. USER STORY
-- ============================================
CREATE TABLE IF NOT EXISTS user_story (
    id BIGSERIAL PRIMARY KEY,

    user_story_number INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(300) NOT NULL,
    acceptance_criteria VARCHAR(500) NOT NULL,

    status VARCHAR(20) DEFAULT 'TODO'
        CHECK (status IN ('TODO', 'IN_PROGRESS', 'DONE')),

    priority VARCHAR(20) DEFAULT 'MEDIUM'
        CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),

    created_by BIGINT NOT NULL,
    assigned_to BIGINT,
    iteration_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,

    estimated_time INT DEFAULT 0,
    actual_time INT DEFAULT 0,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT unique_story_per_iteration
        UNIQUE (iteration_id, user_story_number),

    CONSTRAINT fk_user_story_created_by
        FOREIGN KEY (created_by)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_story_assigned_to
        FOREIGN KEY (assigned_to)
        REFERENCES users(id)
        ON DELETE SET NULL,

    CONSTRAINT fk_user_story_iteration
        FOREIGN KEY (iteration_id)
        REFERENCES iteration(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_story_team
        FOREIGN KEY (team_id)
        REFERENCES team(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_user_story_iteration ON user_story(iteration_id);
CREATE INDEX idx_user_story_assigned_to ON user_story(assigned_to);

-- ============================================
-- 8. TASK
-- ============================================
CREATE TABLE IF NOT EXISTS task (
    id BIGSERIAL PRIMARY KEY,

    task_number INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(300) NOT NULL,

    status VARCHAR(20) DEFAULT 'TODO'
        CHECK (status IN ('TODO', 'IN_PROGRESS', 'DONE')),

    priority VARCHAR(20) DEFAULT 'MEDIUM'
        CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),

    user_story_id BIGINT NOT NULL,
    assigned_to BIGINT,

    estimated_time INT DEFAULT 0,
    actual_time INT DEFAULT 0,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT unique_task_per_story
        UNIQUE (user_story_id, task_number),

    CONSTRAINT fk_task_user_story
        FOREIGN KEY (user_story_id)
        REFERENCES user_story(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_task_assigned_to
        FOREIGN KEY (assigned_to)
        REFERENCES users(id)
        ON DELETE SET NULL
);

CREATE INDEX idx_task_user_story ON task(user_story_id);
CREATE INDEX idx_task_assigned_to ON task(assigned_to);

-- ============================================
-- 9. DEFECT
-- ============================================
CREATE TABLE IF NOT EXISTS defect (
    id BIGSERIAL PRIMARY KEY,

    title VARCHAR(100) NOT NULL,
    description VARCHAR(300) NOT NULL,

    status VARCHAR(20) DEFAULT 'TODO'
        CHECK (status IN ('TODO', 'IN_PROGRESS', 'DONE')),

    priority VARCHAR(20) DEFAULT 'MEDIUM'
        CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),

    assigned_to BIGINT,
    created_by BIGINT,

    user_story_id BIGINT NOT NULL,
    iteration_id BIGINT NOT NULL,

    estimated_time INT DEFAULT 0,
    actual_time INT DEFAULT 0,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT unique_defect_per_iteration
        UNIQUE (user_story_id, iteration_id),

    CONSTRAINT fk_defect_user_story
        FOREIGN KEY (user_story_id)
        REFERENCES user_story(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_defect_iteration
        FOREIGN KEY (iteration_id)
        REFERENCES iteration(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_defect_assigned_to
        FOREIGN KEY (assigned_to)
        REFERENCES users(id)
        ON DELETE SET NULL,

    CONSTRAINT fk_defect_created_by
        FOREIGN KEY (created_by)
        REFERENCES users(id)
        ON DELETE SET NULL
);

CREATE INDEX idx_defect_story_iteration 
ON defect(user_story_id, iteration_id);

-- ============================================
-- 10. ATTACHMENT
-- ============================================
CREATE TABLE IF NOT EXISTS attachment (
    id BIGSERIAL PRIMARY KEY,

    file_url VARCHAR(500) NOT NULL,
    file_type VARCHAR(100),

    uploaded_by BIGINT NOT NULL,

    user_story_id BIGINT,
    task_id BIGINT,
    defect_id BIGINT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_attachment_uploaded_by
        FOREIGN KEY (uploaded_by)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_attachment_user_story
        FOREIGN KEY (user_story_id)
        REFERENCES user_story(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_attachment_task
        FOREIGN KEY (task_id)
        REFERENCES task(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_attachment_defect
        FOREIGN KEY (defect_id)
        REFERENCES defect(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_attachment_one_parent
        CHECK (
            (user_story_id IS NOT NULL)::int +
            (task_id IS NOT NULL)::int +
            (defect_id IS NOT NULL)::int = 1
        )
);

CREATE INDEX idx_attachment_user_story ON attachment(user_story_id);
CREATE INDEX idx_attachment_task ON attachment(task_id);
CREATE INDEX idx_attachment_defect ON attachment(defect_id);
CREATE INDEX idx_attachment_uploaded_by ON attachment(uploaded_by);


-- Default roles:

INSERT INTO roles (name) VALUES
('USER'),
('SCRUM'),
('ADMIN')
ON CONFLICT (name) DO NOTHING;