# UpdateFixerUpper

---

UpdateFixerUpper is a mod designed to help when updating mods, to prevent world corruption and replace items with equivalents

***REMEMBER TO MAKE BACKUPS! UFU DOES NOT CARE WHAT IT IS CHANGING***

# Using UFU
1. `ufu.txt` is the file that controls replacements, the file is created in the config folder the first time you run the game with UFU installed, but you can create it yourself.
2. Replacements are handled in pairs, with a pair being a line in `ufu.txt` of the format `<old identifier> -> <new identifier>`
3. On world load, UFU will replace *any* identifier matching `<old identifier>` with `<new identifier>` when `<old identifier>` is used as a key in a `DefaultedRegistry`

---

(Note: Although the name may imply it, UFU does not use DFU (data fixer upper), it was originally planned to, but that fell through and I decided to keep the name)