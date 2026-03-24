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
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-002',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-003',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-004',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-005',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-006',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-007',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-008',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-009',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-010',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-011',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-012',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-013',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-014',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-015',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-016',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-017',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-018',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-019',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-020',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-021',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-022',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-023',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-024',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-025',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-026',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-027',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-028',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-029',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-030',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-031',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-032',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-033',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-034',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-035',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-036',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-037',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-038',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-039',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-040',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-041',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-042',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-043',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-044',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-045',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-046',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-047',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-048',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-049',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-050',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-051',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-052',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-053',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-054',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-055',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-056',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-057',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-058',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-059',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-060',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-061',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-062',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-063',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-064',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-065',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-066',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-067',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-068',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-069',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-070',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-071',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-072',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-073',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-074',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-075',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-076',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-077',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-078',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-079',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-080',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-081',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-082',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-083',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-084',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-085',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-086',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-087',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-088',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-089',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-090',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-091',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-092',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-093',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-094',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-095',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-096',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-097',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-098',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-099',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-100',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-101',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-102',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-103',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-104',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-105',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-106',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-107',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-108',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-109',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-110',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-111',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-112',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-113',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-114',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-115',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-116',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-117',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-118',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-119',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-120',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-121',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-122',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-123',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-124',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-125',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-126',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-127',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-128',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-129',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-130',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-131',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-132',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-133',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-134',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-135',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-136',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-137',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-138',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-139',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-140',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-141',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-142',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-143',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-144',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-145',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-146',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-147',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-148',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-149',
        '3F East',
        NOW(),
        NOW()
    ),
    (
        'S-150',
        '3F East',
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