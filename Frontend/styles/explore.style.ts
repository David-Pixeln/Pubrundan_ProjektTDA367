import { CustomTheme } from '@constants/themes';
import { StyleSheet } from 'react-native';



export const ExplorePageStyles = (theme: CustomTheme) => StyleSheet.create({
  container: {
    flex: 1,
  },
  map: {
    width: '100%',
    height: '100%',
  },
  topButtonsContainer: {
    width: '100%',
    position: 'absolute',
    top: 0,
    left: 0,
    paddingHorizontal: 16,

    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  button: {
    width: 52,
    aspectRatio: 1,
    
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',

    borderRadius: 50,
    backgroundColor: theme.colors.background.s600,

    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 12,
    },
    shadowOpacity: 0.58,
    shadowRadius: 16.00,
    
    elevation: 24,
  },
  buttonPressed: {
    backgroundColor: theme.colors.background.s200,
  },
  searchIcon: {
    transform: [{rotate: '90deg'}],
  },
});