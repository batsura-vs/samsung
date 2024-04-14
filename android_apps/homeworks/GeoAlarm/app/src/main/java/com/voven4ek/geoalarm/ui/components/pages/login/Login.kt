package com.voven4ek.geoalarm.ui.components.pages.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.voven4ek.geoalarm.R
import com.voven4ek.geoalarm.auth.rememberFirebaseAuthLauncher

@Composable
fun Login(
    child: @Composable () -> Unit
) {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val launcher = rememberFirebaseAuthLauncher(onAuthComplete = { result ->
        user = result.user
    }, onAuthError = {
        user = null
    })
    val token = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current
    if (user == null) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    """
                 Welcome!
                     GeoAlarm
             """.trimIndent(),
                    fontSize = 48.sp,
                    lineHeight = 50.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
            Box(
                modifier = Modifier.weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                OutlinedButton(
                    onClick = {
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(token).requestEmail().build()
                        val googleSignInClient = GoogleSignIn.getClient(context, gso)
                        launcher.launch(googleSignInClient.signInIntent)
                    },
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = BitmapPainter(
                                ImageBitmap.imageResource(
                                    context.resources,
                                    R.drawable.google
                                )
                            ), contentDescription = "Google"
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text("Sign in via Google")
                    }
                }
            }
        }
    } else {
        child()
    }
}