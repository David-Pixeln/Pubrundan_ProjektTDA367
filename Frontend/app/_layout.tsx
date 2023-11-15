import { SplashScreen, Stack } from 'expo-router';
import { SafeAreaProvider, useSafeAreaInsets } from 'react-native-safe-area-context';
import { useEffect } from 'react';
import { useFonts } from 'expo-font';

import FontAwesome from '@expo/vector-icons/FontAwesome';
import { ThemeContext, DarkTheme, CustomTheme } from '@constants/themes';
import { StatusBar, setStatusBarStyle } from 'expo-status-bar';


export {
  // Catch any errors thrown by the Layout component.
  ErrorBoundary,
} from 'expo-router';

export const unstable_settings = {
  // Ensure that reloading on `/...` keeps a back button present.
  initialRouteName: '(tabs)',
};

// Prevent the splash screen from auto-hiding before asset loading is complete.
SplashScreen.preventAutoHideAsync();

export default function RootLayout() {
  const [fontsLoaded, fontsError] = useFonts({
    SpaceMono: require('../assets/fonts/SpaceMono-Regular.ttf'),
    ...FontAwesome.font,
  });

  // Expo Router uses Error Boundaries to catch errors in the navigation tree.
  useEffect(() => {
    if (fontsError) throw fontsError;
  }, [fontsError]);

  useEffect(() => {
    if (fontsLoaded) {
      SplashScreen.hideAsync();
    }
  }, [fontsLoaded]);

  if (!fontsLoaded) {
    return null;
  }

  return <RootLayoutNav />;
}


function RootLayoutNav() {
  const theme: CustomTheme = DarkTheme;

  return (
    <SafeAreaProvider>
      <ThemeContext.Provider value={theme}>
        <StatusBar
          animated={true}
          style={ theme.dark ? 'light' : 'dark' }
        />
        <Stack>
          <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
        </Stack>
      </ThemeContext.Provider>
    </SafeAreaProvider>
  );
}

