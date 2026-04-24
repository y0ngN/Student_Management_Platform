-- Cleanup duplicate "材料上传" menu trees and role-menu relations.
-- Run this in the same database used by RuoYi.

-- 1) Find top-level duplicated menu ids by name/path/perms.
set @dup_root_id := (
  select menu_id
  from sys_menu
  where (
      menu_name = '材料上传'
      or path = 'material'
      or component like 'biz/material%'
      or perms like 'biz:material%'
    )
    and menu_id not in (2000, 2001, 2002, 2003)
  order by menu_id
  limit 1
);

-- 2) Remove role-menu mappings for this tree.
delete rm
from sys_role_menu rm
join sys_menu m on rm.menu_id = m.menu_id
where m.menu_id = @dup_root_id
   or m.parent_id = @dup_root_id
   or m.parent_id in (select menu_id from sys_menu where parent_id = @dup_root_id);

-- 3) Remove button menus under the duplicated tree.
delete from sys_menu
where parent_id in (select menu_id from (select menu_id from sys_menu where parent_id = @dup_root_id) t)
   or parent_id = @dup_root_id;

-- 4) Remove duplicated root menu itself.
delete from sys_menu where menu_id = @dup_root_id;

-- 5) Optional: verify remaining business menus.
select menu_id, menu_name, parent_id, path, component, perms
from sys_menu
where menu_id in (2000, 2001, 2002, 2003)
   or parent_id in (2000, 2001, 2002, 2003)
order by menu_id;
