import { DefaultTheme as ReactDefaultTheme, ThemeProvider, Theme } from '@react-navigation/native';


type CustomTheme = {
  dark: boolean;
  colors: {
    primary: string;
    secondary: string;
    background: string;
    card: string;
    text: string;
    border: string;
    notification: string;
  };
}


const tintColorLight = '#2f95dc';
const tintColorDark = '#414361';

export default {
  light: {
    text: '#000',
    background: '#fff',
    tint: tintColorLight,
    tabIconDefault: '#ccc',
    tabIconSelected: tintColorLight,
  },
  dark: {
    text: '#fff',
    background: '#2A2D43',
    tint: tintColorDark,
    tabIconDefault: '#ccc',
    tabIconSelected: tintColorDark,
  },
};

export const LightTheme: CustomTheme = {
  ...ReactDefaultTheme,
  colors: {
    ...ReactDefaultTheme.colors,
    secondary: '#FED148',
  }
}

export const DarkTheme: CustomTheme = {
  dark: true,
  colors: {
    primary: '#FED148',
    secondary: '#FED148',
    background: '#2A2D43',
    text: '#FFFFFF',
    card: '#414361',
    border: '#414361',
    notification: '#FED148',
  }
}