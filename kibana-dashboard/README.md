## Create a Play&Go dashboard

Instructions to create a new Play&Go Dashboard for a game with id GAMEID:

1. Create on Kibana a new index gamification-stats-GAMEID-* with executionTime as temporal field
2. Add to index following 4 scripted field
2a. name: dayofWeekStr
     lang: painless
     type: string
     format: default
     popularity: 0
     script:
       ES5 def days = ['Lu', 'Ma', 'Me', 'Gi', 'Ve', 'Sa', 'Do'];
       return days[doc["executionTime"].date.dayOfWeek - 1] 
       ES7 def days = ['Lu', 'Ma', 'Me', 'Gi', 'Ve', 'Sa', 'Do'];
       return days[doc["executionTime"].value.getDayOfWeek() - 1]
2b. name: dayType
     lang: painless
     type: number
     format: default
     popularity: 2
     script:
       ES5 doc["executionTime"].date.dayOfWeek < 6 ? 1: 2
       ES7 doc["executionTime"].value.getDayOfWeek() < 6 ? 1: 2
2c. name: hourOfDay
     lang: painless
     type: number
     format: default
     popularity: -11
     script:
       ES5 doc["executionTime"].date.hourOfDay
       ES7 doc["executionTime"].value.getHour()
2d. name: dayOfWeek
     lang: painless
     type: number
     format: default
     popularity: 0
     script:
      ES5 (doc["executionTime"].date.dayOfWeek + 1) % 7 + 1
      ES7 (doc["executionTime"].value.getDayOfWeek() + 1) % 7 + 1
