//קובץ מקור זה, מגדיר את האובייקט ואת חוקי התקינות של שם המשתמש והסיסמה

package com.example; 
// הגדרת החבילה בה נמצא הקובץ ,כדי שהקוד יתקמפל בכלי של מייבן

public class User 
// הגדרת המחלקה הציבורית , המייצגת משתמש אחד במערכת
{ 
    
    private String username;
//שדה משתנה פרטי, לשמירת שם המשתמש  
    private String password; 
// שדה משתנה פרטי, לשמירת הסיסמה של המשתמש 

    public User(String username, String password) throws Exception 
// בנאי המחלקה, המקבל שם וסיסמה, ומצהיר שהוא עלול לזרוק שגיאות    
    { 
        if (username.length() > 50) 
// תנאי ראשון: בודק אם אורך שם המשתמש עולה על 50 תווים
        { 
            throw new Exception("Username is too long, try something shorter");
// זורק שגיאה עם ההודעה המדויקת מטבלת השגיאות במטלה
        } 


String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";       
// ביטוי רגולרי (רגקס), לאימות המייל לפי הכללים 
// אותיות/ספרות/תווים מסוימים, מופרד ב-@ ובנקודה         
// הגדרת תבנית החוקים, עבור פורמט האימייל
        
        if (!username.matches(emailRegex)) 
// תנאי הבודק, האם שם המשתמש אינו תואם לתבנית שהגדרנו            
        { 
            throw new Exception("Please enter a valid Email as username"); 
// זורק שגיאה, עם ההודעה המתאימה, עבור פורמט מייל שגוי
        } 

        if (password.length() < 8) 
// תנאי הבודק, אם אורך הסיסמה קצר מ-8 תווים.            
        { 
            throw new Exception("Your password is too short, add more characters");
// זורק שגיאה, עבור סיסמה קצרה מדי.
        } 

        if (password.length() > 12) 
// תנאי הבודק, אם אורך הסיסמה ארוך מ-12 תווים
        { 
            throw new Exception("Your password is too long, try a shorter one"); 
// זורק שגיאה עבור סיסמה ארוכה מדי
        } 

        
        String passRegex = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,16}$";
// ביטוי רגולרי לאימות הסיסמה 
// חובה לפחות אות אחת, חובה לפחות ספרה אחת, וחובה לפחות סימן אחד         
// הגדרת התבנית ,שמוודאת הימצאות של שלושת סוגי התווים
        
        if (!password.matches(passRegex)) 
// תנאי הבודק ,האם הסיסמה אינה מקיימת את החוקים (חסרה אות, ספרה או סימן)            
        { 
            throw new Exception("Please enter a valid password"); 
// זורק שגיאה, עבור סיסמה המכילה הרכב תווים לא חוקי
        } 
// אם הקוד הגיע עד לכאן, מבלי לזרוק שגיאה, הנתונים תקינים ולכן ניתן לשמור אותם בשדות האובייקט
        this.username = username; 
//שמירת שם המשתמש התקין, בתוך משתנה המחלקה של האובייקט
        this.password = password; 
// שמירת הסיסמה התקינה, בתוך משתנה המחלקה של האובייקט
    } 
// סגירת הבנאי.

    public String getUsername() 
// מתודה  ציבורית, שמאפשרת לקרוא את שם המשתמש מחוץ למחלקה    
    { 
        return this.username; 
// מחזירה את הערך של שם המשתמש.
    } 

    @Override 
//  פקודה המציינת שאנחנו "דורסים", את פונקציית ההדפסה של ג'אווה
    public String toString() 
// מתודה שמגדירה איך האובייקט יוצג כטקסט ,כשנרצה להדפיס אותו    
    { 
        return this.username + " " + this.password; 
// מחזירה את שם המשתמש והסיסמה מופרדים ברווח
    } 
} 
// סגירת המחלקה 
// User