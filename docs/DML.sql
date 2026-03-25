-- Run this file after DDL.sql on database: "office-navi".
BEGIN;

-- Reset data, then insert fixed dummy rows.
TRUNCATE TABLE user_seats, seats, users RESTART IDENTITY CASCADE;

INSERT INTO
    users (
        name,
        email,
        password_hash,
        role_code,
        created_at,
        updated_at
    )
VALUES (
        'User 001',
        'officenavi_user_001@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        0,
        NOW(),
        NOW()
    ),
    (
        'User 002',
        'officenavi_user_002@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 003',
        'officenavi_user_003@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 004',
        'officenavi_user_004@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 005',
        'officenavi_user_005@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 006',
        'officenavi_user_006@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 007',
        'officenavi_user_007@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 008',
        'officenavi_user_008@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 009',
        'officenavi_user_009@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 010',
        'officenavi_user_010@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 011',
        'officenavi_user_011@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 012',
        'officenavi_user_012@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 013',
        'officenavi_user_013@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 014',
        'officenavi_user_014@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 015',
        'officenavi_user_015@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 016',
        'officenavi_user_016@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 017',
        'officenavi_user_017@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 018',
        'officenavi_user_018@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 019',
        'officenavi_user_019@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 020',
        'officenavi_user_020@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 021',
        'officenavi_user_021@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 022',
        'officenavi_user_022@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 023',
        'officenavi_user_023@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 024',
        'officenavi_user_024@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 025',
        'officenavi_user_025@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 026',
        'officenavi_user_026@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 027',
        'officenavi_user_027@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 028',
        'officenavi_user_028@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 029',
        'officenavi_user_029@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 030',
        'officenavi_user_030@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 031',
        'officenavi_user_031@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 032',
        'officenavi_user_032@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 033',
        'officenavi_user_033@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 034',
        'officenavi_user_034@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 035',
        'officenavi_user_035@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 036',
        'officenavi_user_036@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 037',
        'officenavi_user_037@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 038',
        'officenavi_user_038@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 039',
        'officenavi_user_039@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 040',
        'officenavi_user_040@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 041',
        'officenavi_user_041@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 042',
        'officenavi_user_042@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 043',
        'officenavi_user_043@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 044',
        'officenavi_user_044@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 045',
        'officenavi_user_045@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 046',
        'officenavi_user_046@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 047',
        'officenavi_user_047@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 048',
        'officenavi_user_048@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 049',
        'officenavi_user_049@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 050',
        'officenavi_user_050@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 051',
        'officenavi_user_051@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 052',
        'officenavi_user_052@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 053',
        'officenavi_user_053@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 054',
        'officenavi_user_054@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 055',
        'officenavi_user_055@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 056',
        'officenavi_user_056@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 057',
        'officenavi_user_057@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 058',
        'officenavi_user_058@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 059',
        'officenavi_user_059@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 060',
        'officenavi_user_060@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 061',
        'officenavi_user_061@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 062',
        'officenavi_user_062@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 063',
        'officenavi_user_063@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 064',
        'officenavi_user_064@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 065',
        'officenavi_user_065@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 066',
        'officenavi_user_066@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 067',
        'officenavi_user_067@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 068',
        'officenavi_user_068@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 069',
        'officenavi_user_069@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 070',
        'officenavi_user_070@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 071',
        'officenavi_user_071@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 072',
        'officenavi_user_072@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 073',
        'officenavi_user_073@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 074',
        'officenavi_user_074@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 075',
        'officenavi_user_075@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 076',
        'officenavi_user_076@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 077',
        'officenavi_user_077@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 078',
        'officenavi_user_078@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 079',
        'officenavi_user_079@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 080',
        'officenavi_user_080@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 081',
        'officenavi_user_081@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 082',
        'officenavi_user_082@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 083',
        'officenavi_user_083@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 084',
        'officenavi_user_084@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 085',
        'officenavi_user_085@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 086',
        'officenavi_user_086@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 087',
        'officenavi_user_087@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 088',
        'officenavi_user_088@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 089',
        'officenavi_user_089@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 090',
        'officenavi_user_090@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 091',
        'officenavi_user_091@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 092',
        'officenavi_user_092@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 093',
        'officenavi_user_093@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 094',
        'officenavi_user_094@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 095',
        'officenavi_user_095@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 096',
        'officenavi_user_096@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 097',
        'officenavi_user_097@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 098',
        'officenavi_user_098@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 099',
        'officenavi_user_099@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    ),
    (
        'User 100',
        'officenavi_user_100@example.com',
        '$2a$10$veZ3ZZCH4mA3eO4BvzdBiOZ67CSILv3KUr4vUtNJ50u7gjAUWYHY.',
        1,
        NOW(),
        NOW()
    );

-- Promote one bootstrap account to ADMIN (0). Others remain GENERAL (1 by default).
UPDATE users
SET
    role_code = 0,
    updated_at = NOW()
WHERE
    email = 'officenavi_user_001@example.com';

INSERT INTO
    seats (
        name,
        location,
        created_at,
        updated_at
    )
VALUES (
        'S-001',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-002',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-003',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-004',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-005',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-006',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-007',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-008',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-009',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-010',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-011',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-012',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-013',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-014',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-015',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-016',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-017',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-018',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-019',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'S-020',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-001',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-002',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-003',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-004',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-005',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-006',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-007',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-008',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-009',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-010',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-011',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-012',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-013',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-014',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-015',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-016',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-017',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-018',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-019',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-020',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-021',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-022',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-023',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-024',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-025',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-026',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-027',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-028',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-029',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-030',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-031',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-032',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-033',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-034',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-035',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-036',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-037',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-038',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-039',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-040',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-041',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-042',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-043',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-04',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-045',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-046',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-047',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-048',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-049',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-050',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-051',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-052',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-053',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-054',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-055',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-056',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-057',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-058',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-059',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'A-060',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-001',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-002',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-003',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-004',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-005',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-006',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-007',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-008',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-009',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-010',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-011',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-012',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-013',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-014',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-015',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-016',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-017',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-018',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-019',
        '6F ラーセン',
        NOW(),
        NOW()
    ),
    (
        'B-020',
        '6F ラーセン',
        NOW(),
        NOW()
    );

SELECT (
        SELECT COUNT(*)
        FROM users
    ) AS users_total, (
        SELECT COUNT(*)
        FROM seats
    ) AS seats_total;

COMMIT;