## Create a Play&Go dashboard

Instructions to create a new Play&Go Dashboard for a game with id GAMEID:

1. Create on Kibana a new index gamification-stats-GAMEID-* with executionTime as temporal field
2. Add to index following 4 scripted field
     * ```
       name: dayofWeekStr
       lang: painless
       type: string
       format: default
       popularity: 0
       script:
       ES5 
       def days = ['Lu', 'Ma', 'Me', 'Gi', 'Ve', 'Sa', 'Do'];
       return days[doc["executionTime"].date.dayOfWeek - 1] 
       ES7 
       def days = ['Lu', 'Ma', 'Me', 'Gi', 'Ve', 'Sa', 'Do'];
       return days[doc["executionTime"].value.getDayOfWeek() - 1]
       ```
     * ```
       name: dayType
       lang: painless
       type: number
       format: default
       popularity: 2
       script:
       ES5 
       doc["executionTime"].date.dayOfWeek < 6 ? 1: 2
       ES7 
       doc["executionTime"].value.getDayOfWeek() < 6 ? 1: 2
       ```
     * ```
       name: hourOfDay
       lang: painless
       type: number
       format: default
       popularity: -11
       script:
       ES5 
       doc["executionTime"].date.hourOfDay
       ES7 
       doc["executionTime"].value.getHour()
       ```
     * ```
       name: dayOfWeek
       lang: painless
       type: number
       format: default
       popularity: 0
       script:
       ES5 
       (doc["executionTime"].date.dayOfWeek + 1) % 7 + 1
       ES7 
       (doc["executionTime"].value.getDayOfWeek() + 1) % 7 + 1
       ```

3. Duplicate file `template/playAndGo_template_visualizations.json`
4. In the duplicated file replace all occurrences of `59a91478e4b0c9db6800afaf` with the GAMEID target [expected 45 occurrences ]
5. Replace all occurrences of `Play&Go 2017` with a new title (i.e: `Play&Go 20XX`) [expected 74 occurrences]
6. Load the file on Kibana
7. Export from Kibana all visualization of `Play&go 20XX` ( Kibana will provide the `_id` field to visualizations loaded at step 6)
   in un file chiamato "visualizzazioni 20XX con id.json"
8. Copy `template/playAndGo_template_dashboard.json` in `playAndGo dashboard 20XX.json`
9. Open the duplicated file and change the `title` field
10. Open script `template/mapping_id.py` and configure the paths.
11. Run the script `template/mapping_id.py` to bind visualizations produced at step 7 with new dashboard. 
    The ouptut file be `dashboard.mapped.20XX.json`
12. Import on Kibana file `dashboard.mapped.20XX.json`

**NOTE**
Script `template/extract_id_title_template.py` is used to produce file `template_mapping_id.csv` (required by script `template/mapping_id.py`)
The file contains the bind between `id`s and visualization names. At the moment it is created using `Play&Go 2017` as reference
