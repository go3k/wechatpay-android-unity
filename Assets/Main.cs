using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Main : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        WXPay.Instance.InitSDK("wxd930ea5d5a258f4f");
    }

    public void OnPayClick()
    {
        WXPay.Instance.Pay("1900000109", "1101000000140415649af9fc314aa427", 
            "1101000000140429eb40476f8896f4c9", "1398746574", "7FFECB600D7157C5AA49810D2D8F28BC2811827B", 
            "ext", o =>
            {
                Debug.Log($"Pay Callback {o.result}");
            });
    }
    
    // Update is called once per frame
    void Update()
    {
        
    }
}
